'use client'

import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'
import { useContext, useEffect, useState } from 'react'
import process from 'process'
import { fetchWithAuthorization } from '@/components/hooks/CustomFetch'
import { AuthContext } from '@/components/hooks/useAuth'

export default function CommentInput({ contentId, onCommentAdded }) {
  const { accessToken, setAccessToken } = useContext(AuthContext)
  const [comment, setComment] = useState('')

  const handleCommentSubmit = async () => {
    const data = {
      comment: comment,
    }

    if (comment === '') {
      alert('댓글 내용을 입력해주세요')
      return
    }

    const response = await fetchWithAuthorization(
      process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${contentId}/comments`,
      'POST',
      accessToken,
      data
    )

    if (response.headers.has('Access-Token')) {
      const newAccessToken = response.headers.get('Access-Token')
      newAccessToken && setAccessToken(newAccessToken)
    }

    if (response.ok) {
      onCommentAdded()
      setComment('')
    } else if (response.status == 401) {
      alert('로그인을 해주세요')
      return
    } else {
      alert('댓글 등록 실패(내용이 올바르지 않습니다)')
      return
    }
  }

  const handleCommentChange = (e) => {
    setComment(e.target.value)
  }

  return (
    <Box
      component="form"
      sx={{
        '& > :not(style)': { m: 1, width: '100%' },
      }}
      noValidate
      autoComplete="off"
    >
      <TextField
        id="outlined-basic"
        label="댓글 작성"
        variant="outlined"
        multiline
        onChange={handleCommentChange}
        value={comment}
      />
      <Button variant="contained" onClick={handleCommentSubmit}>
        {' '}
        작성 완료{' '}
      </Button>
    </Box>
  )
}
