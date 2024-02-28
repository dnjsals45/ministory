'use client';

import Container from '@/components/Container';
import { useEffect, useState } from 'react';
import { Button } from '@mui/material';

const CreatePost = (props) => {
  const id: number = props.params.id;
  // console.log('props: ', props);
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
        console.log('글 작성 성공.');
      } else {
        console.error('글 작성 실패');
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
        console.log('글 작성 성공.');
      } else {
        console.error('글 작성 실패');
      }
    } catch (error) {
      console.error('글 작성 중 오류가 발생했습니다.', error);
    }
  };

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleBodyChange = (e) => {
    setBody(e.target.value);
  };

  return (
    <Container>
      <h1> 글 작성 </h1>
      <form>
        <input
          type={'text'}
          placeholder={'제목을 입력해주세요'}
          value={title}
          onChange={handleTitleChange}
        />
        <input
          type={'text'}
          placeholder={'글 내용을 입력해주세요'}
          value={body}
          onChange={handleBodyChange}
        />
        <Button onClick={handleComplete}>글 생성</Button>
        <Button onClick={handleTemporary}>임시저장</Button>
      </form>
    </Container>
  );
};

export default CreatePost;
