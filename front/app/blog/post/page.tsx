'use client'

import {
  Autocomplete,
  Button,
  TextField,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  DialogContentText,
} from '@mui/material'
import { useContext, useEffect, useState } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'
import { useRouter } from 'next/navigation'
import { TagContext } from '@/components/hooks/useTag'
import dynamic from 'next/dynamic'
import process from 'process'
import { fetchWithAuthorization } from '@/components/hooks/CustomFetch'

const MyEditorWithNoSSR = dynamic(() => import('@/components/MyEditor'), {
  ssr: false,
})

interface Content {
  contentId: number
  uuid: string
}

export default function NewPost() {
  const router = useRouter()
  const [title, setTitle] = useState('')
  const [body, setBody] = useState('')
  const [selectedTags, setSelectedTags] = useState<string[]>([])
  const [openDialog, setOpenDialog] = useState<boolean>(false)
  const [newTag, setNewTag] = useState<string>('')
  const { accessToken, setAccessToken } = useContext(AuthContext)
  const { tags, fetchContentTag, fetchRegisterTag } = useContext(TagContext)
  const [tagOptions, setTagOptions] = useState<string[]>([
    '+ 태그 추가',
    ...tags.map((tag) => tag.tagName),
  ])

  useEffect(() => {
    setTagOptions(['+ 태그 추가', ...tags.map((tag) => tag.tagName)])
  }, [tags])

  const handleComplete = async () => {
    const content = {
      title: title,
      body: body,
      complete: true,
      tags: selectedTags,
    }

    const contentResponse = await fetchWithAuthorization(
      process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents`,
      'POST',
      accessToken,
      content
    )

    if (contentResponse.headers.has('Access-Token')) {
      const newAccessToken = contentResponse.headers.get('Access-Token')
      newAccessToken && setAccessToken(newAccessToken)
    }

    if (contentResponse.ok) {
      alert('게시글 작성 성공!')
      await fetchContentTag()
      await fetchRegisterTag()
      router.push('/blog')
    } else {
      alert('게시글 작성 실패....')
      return
    }
  }

  const handleTemporary = async () => {
    const content = {
      title: title,
      body: body,
      complete: false,
      tags: selectedTags,
    }

    const contentResponse = await fetchWithAuthorization(
      process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents`,
      'POST',
      accessToken,
      content
    )

    if (contentResponse.headers.has('Access-Token')) {
      const newAccessToken = contentResponse.headers.get('Access-Token')
      newAccessToken && setAccessToken(newAccessToken)
    }

    if (contentResponse.ok) {
      const response: { data: Content } = await contentResponse.json()
      router.push(`/blog/edit/${response.data.uuid}`)
    } else {
      alert('임시저장 실패....')
      return
    }
  }

  const handleTitleChange = (e) => {
    setTitle(e.target.value)
  }

  const handleBodyChange = (contentHtml: string) => {
    setBody(contentHtml)
  }

  const handleTagsChange = (event, newValue) => {
    if (newValue.includes('+ 태그 추가')) {
      setOpenDialog(true)
      return
    }
    setSelectedTags(newValue)
  }

  const handleAddTag = async () => {
    if (newTag) {
      const registerTagResponse = await fetchWithAuthorization(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/tags/register`,
        'POST',
        accessToken,
        { tagName: newTag }
      )

      if (registerTagResponse.headers.has('Access-Token')) {
        const newAccessToken = registerTagResponse.headers.get('Access-Token')
        newAccessToken && setAccessToken(newAccessToken)
      }

      if (registerTagResponse.ok) {
        alert('새로운 태그 등록 성공')
        setTagOptions((prevTagOptions) => [...prevTagOptions, newTag])
        setSelectedTags([...selectedTags, newTag])
        setNewTag('')
        setOpenDialog(false)
        await fetchRegisterTag()
      } else {
        alert('새로운 태그 등록 실패')
      }
    }
  }

  return (
    <div>
      <div className={'my-10'}>
        <h1>제목</h1>
        <TextField
          required
          id="outlined-required"
          value={title}
          placeholder={'제목을 입력해주세요'}
          onChange={handleTitleChange}
          style={{ width: `100%` }}
        />
      </div>
      <div className={'my-10'}>
        <div>태그 선택:</div>
        <Autocomplete
          multiple
          id="tags"
          options={tagOptions}
          getOptionLabel={(option) => option}
          value={selectedTags}
          onChange={handleTagsChange}
          renderInput={(params) => (
            <TextField {...params} variant="standard" placeholder="태그를 선택하세요" />
          )}
        />
        {selectedTags.length > 0 && (
          <div className="selected-tags">
            선택한 태그: <strong>{selectedTags.join(', ')}</strong>
          </div>
        )}

        <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
          <DialogTitle>새 태그 추가</DialogTitle>
          <DialogContent>
            <DialogContentText>새로 생성할 태그를 입력해주세요.</DialogContentText>
            <TextField
              /* eslint-disable-next-line jsx-a11y/no-autofocus */
              autoFocus
              required
              margin="dense"
              label="태그명"
              type="text"
              fullWidth
              variant="standard"
              value={newTag}
              onChange={(e) => setNewTag(e.target.value)}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpenDialog(false)}>취소</Button>
            <Button onClick={handleAddTag}>추가</Button>
          </DialogActions>
        </Dialog>
      </div>
      <div>
        <MyEditorWithNoSSR
          onChangeContent={handleBodyChange}
          initialValue={''}
          accessToken={accessToken}
        />
      </div>
      <Button onClick={handleComplete}>글 생성</Button>
      <Button onClick={handleTemporary}>임시저장</Button>
    </div>
  )
}
