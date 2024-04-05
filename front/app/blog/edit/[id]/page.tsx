'use client'

import { useContext, useEffect, useState } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'
import dynamic from 'next/dynamic'
import { Button, TextField } from '@mui/material'
import { TagContext } from '@/components/hooks/useTag'
import { ContentDetail } from '@/data/ContentDetail'
import process from 'process'
import { useRouter } from 'next/navigation'

const MyEditorWithNoSSR = dynamic(() => import('@/components/MyEditor'), {
  ssr: false,
})

async function fetchContentData(id: number): Promise<{ data: ContentDetail }> {
  const data = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  })
  return data.json()
}

export default function EditContent(props) {
  const id = props.params.id
  const router = useRouter()
  const [detail, setDetail] = useState<ContentDetail | undefined>(undefined)
  const [title, setTitle] = useState('')
  const [body, setBody] = useState('')
  const [selectedTags, setSelectedTags] = useState<string[]>([])
  const { accessToken } = useContext(AuthContext)
  const { tags, fetchTags } = useContext(TagContext)

  useEffect(() => {
    const contentData = async (id: number) => {
      const response = await fetchContentData(id)
      setDetail(response.data)
      setTitle(response.data.content.title)
      setBody(response.data.content.body)
    }

    contentData(id)
  }, [id])

  const handleEditComplete = async () => {
    const content = {
      title: title,
      body: body,
      complete: true,
    }

    const contentResponse = await fetch(
      process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`,
      {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: 'Bearer ' + accessToken,
        },
        body: JSON.stringify(content),
      }
    )

    // const tagResponse = await fetch(
    //   process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}/tags`,
    //   {
    //     method: 'POST',
    //     headers: {
    //       'Content-Type': 'application/json;charset=UTF-8',
    //       Authorization: 'Bearer ' + accessToken,
    //     },
    //     body: JSON.stringify({ tags: selectedTags }),
    //   }
    // )

    if (contentResponse.ok) {
      alert('게시글 수정 성공!')
      // fetchTags()
      router.push('/blog')
    } else {
      alert('게시글 수정 실패....')
    }
  }

  const handleTitleChange = (e) => {
    setTitle(e.target.value)
  }

  const handleBodyChange = (contentHtml: string) => {
    setBody(contentHtml)
  }

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedOptionValue = event.target.value

    if (selectedOptionValue === 'none') {
      setSelectedTags([])
      return
    }
    if (selectedTags.includes(selectedOptionValue)) {
      setSelectedTags(selectedTags.filter((tag) => tag !== selectedOptionValue))
    } else {
      setSelectedTags([...selectedTags, selectedOptionValue])
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
            <select
              value={selectedTags[0]}
              onChange={handleSelectChange}
              className="dropdown-class"
            >
              <option value="none">선택하지 않음</option>
              {tags?.map((t) => (
                <option key={t.tagName} value={t.tagName}>
                  {t.tagName}
                </option>
              ))}
            </select>
            {selectedTags.length > 0 && (
              <div className="selected-tags">
                선택한 태그: <strong>{selectedTags.join(', ')}</strong>
              </div>
            )}
          </div>
          <div>
            <MyEditorWithNoSSR onChangeContent={handleBodyChange} initialValue={body} />
          </div>
          <Button onClick={handleEditComplete}>수정하기</Button>
        </div>
      }
    </div>
  )
}
