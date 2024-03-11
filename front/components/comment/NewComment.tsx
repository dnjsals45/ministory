'use client';

import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { useEffect, useState } from 'react';

const NewComment = ({ contentId, onCommentAdded }) => {
  const [accessToken, setAccessToken] = useState<string>();
  const [comment, setComment] = useState('');

  useEffect(() => {
    const accessToken = localStorage.getItem('access-token');
    if (accessToken != undefined) {
      setAccessToken(accessToken);
    }
  }, []);

  const handleCommentSubmit = async () => {
    const data = {
      comment: comment,
    };

    try {
      const response = await fetch(`http://localhost:8080/api/v1/contents/${contentId}/comments`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: 'Bearer ' + accessToken,
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        onCommentAdded();
        setComment('');
      } else {
        alert('댓글 등록 실패');
      }
    } catch (error) {
      console.error('댓글 등록 중 오류가 발생했습니다.', error);
    }
  };

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };

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
  );
};

export default NewComment;
