'use client'

import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'
import { useEffect, useState } from 'react'

export default function CommentInput({ contentId, onCommentAdded }) {
  const [accessToken, setAccessToken] = useState<string>()
  const [comment, setComment] = useState('')

  useEffect(() => {
    const accessToken = localStorage.getItem('access-token')
    if (accessToken != undefined) {
      setAccessToken(accessToken)
    }
  }, [])

  const handleCommentSubmit = async () => {
    const data = {
      comment: comment,
    }

    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    }

    if (accessToken) {
      requestOptions.headers['Authorization'] = 'Bearer ' + accessToken
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/contents/${contentId}/comments`,
        requestOptions
      )

      if (response.ok) {
        onCommentAdded()
        setComment('')
      } else {
        alert('로그인을 해야 합니다')
      }
    } catch (error) {
      console.error('댓글 등록 중 오류가 발생했습니다.', error)
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
