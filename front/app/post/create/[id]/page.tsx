'use client';

import Container from '@/components/Container';
import { useEffect, useState } from 'react';
import { Button, TextField } from '@mui/material';
import NewPost from '@/components/post/NewPost';
import { useRouter } from 'next/navigation';

const CreatePost = (props) => {
  const id: number = props.params.id;
  const router = useRouter();
  const [title, setTitle] = useState('');
  const [body, setBody] = useState('');
  const [accessToken, setAccessToken] = useState<string>();

  useEffect(() => {
    const accessToken = localStorage.getItem('access-token');
    if (accessToken != undefined) {
      setAccessToken(accessToken);
    }
  }, []);
  const handleComplete = async () => {
    const content = {
      title: title,
      body: body,
      complete: true,
    };

    try {
      const response = await fetch(`http://localhost:8080/api/v1/contents/${id}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: 'Bearer ' + accessToken,
        },
        body: JSON.stringify(content),
      });

      if (response.ok) {
        alert('게시글 작성 성공!');
        router.push('/post');
      } else {
        alert('게시글 작성 실패....');
      }
    } catch (error) {
      console.error('글 작성 중 오류가 발생했습니다.', error);
    }
  };

  const handleTemporary = async () => {
    const content = {
      title: title,
      body: body,
      complete: false,
    };

    try {
      const response = await fetch(`http://localhost:8080/api/v1/contents/${id}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: 'Bearer ' + accessToken,
        },
        body: JSON.stringify(content),
      });

      if (response.ok) {
        alert('임시저장 성공!');
      } else {
        alert('임시저장 실패....');
      }
    } catch (error) {
      console.error('글 작성 중 오류가 발생했습니다.', error);
    }
  };

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleBodyChange = (newContent) => {
    setBody(newContent);
  };

  return (
    <Container>
      <h1> 제목 </h1>
      <TextField
        required
        id="outlined-required"
        value={title}
        placeholder={'제목을 입력해주세요'}
        onChange={handleTitleChange}
      />
      <h1> 본문 </h1>
      <NewPost onChangeContent={handleBodyChange}></NewPost>
      <Button onClick={handleComplete}>글 생성</Button>
      <Button onClick={handleTemporary}>임시저장</Button>
    </Container>
  );
};

export default CreatePost;
