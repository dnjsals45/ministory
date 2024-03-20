'use client'

import MyEditor from '@/components/MyEditor'
import { Button, TextField } from '@mui/material'
import { useContext, useState } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'
import { useRouter } from 'next/navigation'
import { TagContext } from '@/components/hooks/useTag'

export default function NewPost(props) {
  const id = props.params.id
  const router = useRouter()
  const [title, setTitle] = useState('')
  const [body, setBody] = useState('')
  const [selectedTags, setSelectedTags] = useState<string[]>([])
  const { accessToken } = useContext(AuthContext)
  const { tags } = useContext(TagContext)

  const handleComplete = async () => {
    const content = {
      title: title,
      body: body,
      complete: true,
    }

    const contentResponse = await fetch(`http://localhost:8080/api/v1/contents/${id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: 'Bearer ' + accessToken,
      },
      body: JSON.stringify(content),
    })

    const tagResponse = await fetch(`http://localhost:8080/api/v1/contents/${id}/tags`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: 'Bearer ' + accessToken,
      },
      body: JSON.stringify({ tags: selectedTags }),
    })

    if (contentResponse.ok && tagResponse.ok) {
      alert('게시글 작성 성공!')
      router.push('/blog')
    } else if (!contentResponse.ok) {
      alert('게시글 작성 실패....')
    }
  }

  const handleTemporary = async () => {
    const content = {
      title: title,
      body: body,
      complete: false,
    }

    const contentResponse = await fetch(`http://localhost:8080/api/v1/contents/${id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: 'Bearer ' + accessToken,
      },
      body: JSON.stringify(content),
    })

    const tagResponse = await fetch(`http://localhost:8080/api/v1/contents/${id}/tags`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
        Authorization: 'Bearer ' + accessToken,
      },
      body: JSON.stringify({ tags: selectedTags }),
    })

    if (contentResponse.ok && tagResponse.ok) {
      alert('게시글 임시저장 성공!')
    } else if (!contentResponse.ok) {
      alert('게시글 임시저장 실패....')
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
        <select value={selectedTags} onChange={handleSelectChange} className="dropdown-class">
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
        <MyEditor onChangeContent={handleBodyChange} />
      </div>
      <Button onClick={handleComplete}>글 생성</Button>
      <Button onClick={handleTemporary}>임시저장</Button>
    </div>
  )
}
