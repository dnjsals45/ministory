'use client'

import { useContext, useEffect, useState } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'
import dynamic from 'next/dynamic'
import {
  Autocomplete,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from '@mui/material'
import { TagContext } from '@/components/hooks/useTag'
import { ContentDetail } from '@/data/ContentDetail'
import process from 'process'
import { useRouter } from 'next/navigation'
import { fetchWithAuthorization } from '@/components/hooks/CustomFetch'

const MyEditorWithNoSSR = dynamic(() => import('@/components/MyEditor'), {
  ssr: false,
})

async function fetchContentData(id: string): Promise<{ data: ContentDetail }> {
  const response = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  })
  return response.json()
}

export default function EditContent(props) {
  const id = props.params.id
  const router = useRouter()
  const [detail, setDetail] = useState<ContentDetail | undefined>(undefined)
  const [title, setTitle] = useState('')
  const [body, setBody] = useState('')
  const [selectedTags, setSelectedTags] = useState<string[]>([])
  const { accessToken, setAccessToken } = useContext(AuthContext)
  const { tags, fetchContentTag, fetchRegisterTag } = useContext(TagContext)
  const [loading, setLoading] = useState<boolean>(false)
  const [openDialog, setOpenDialog] = useState<boolean>(false)
  const [newTag, setNewTag] = useState<string>('')
  const [tagOptions, setTagOptions] = useState<string[]>([
    '+ 태그 추가',
    ...tags.map((tag) => tag.tagName),
  ])

  useEffect(() => {
    const contentData = async (id: string) => {
      const response = await fetchContentData(id)
      setDetail(response.data)
      setTitle(response.data.content.title)
      setSelectedTags(response.data.content.tags.map((tag) => tag.tagName))
      setBody(response.data.content.body)
      setLoading(true)
    }

    contentData(id)
  }, [loading])

  useEffect(() => {
    setTagOptions(['+ 태그 추가', ...tags.map((tag) => tag.tagName)])
  }, [tags])

  const handleEditComplete = async () => {
    const content = {
      title: title,
      body: body,
      complete: true,
      tags: selectedTags,
    }

    const contentResponse = await fetchWithAuthorization(
      process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`,
      'PATCH',
      accessToken,
      content
    )

    if (contentResponse.headers.has('Access-Token')) {
      const newAccessToken = contentResponse.headers.get('Access-Token')
      newAccessToken && setAccessToken(newAccessToken)
    }

    if (contentResponse.ok) {
      alert('게시글 수정 성공!')
      await fetchContentTag()
      await fetchRegisterTag()
      router.push('/blog')
    } else {
      alert('게시글 수정 실패....')
    }
  }

  const handleEditTemp = async () => {
    const content = {
      title: title,
      body: body,
      complete: false,
      tags: selectedTags,
    }

    const contentResponse = await fetchWithAuthorization(
      process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`,
      'PATCH',
      accessToken,
      content
    )

    if (contentResponse.headers.has('Access-Token')) {
      const newAccessToken = contentResponse.headers.get('Access-Token')
      newAccessToken && setAccessToken(newAccessToken)
    }

    if (contentResponse.ok) {
      alert('게시글 임시저장 성공!')
    } else {
      alert('게시글 임시저장 실패....')
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
      {
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
            {loading && (
              <MyEditorWithNoSSR
                onChangeContent={handleBodyChange}
                initialValue={body}
                accessToken={accessToken}
              />
            )}
          </div>
          <Button onClick={handleEditComplete}>수정하기</Button>
          <Button onClick={handleEditTemp}>임시저장</Button>
        </div>
      }
    </div>
  )
}
