'use client';

import navlinks from '../data/navlinks';
import { useRouter } from 'next/navigation';
import { Button } from '@mui/material';
import { useEffect, useState } from 'react';

const Nav = () => {
  const router = useRouter();
  const [accessToken, setAccessToken] = useState<string | null>(null);

  useEffect(() => {
    setAccessToken(localStorage.getItem('access-token'));
  }, []);

  const createPost = async () => {
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    };

    if (accessToken) {
      requestOptions.headers['Authorization'] = 'Bearer ' + accessToken;
    }

    const response = await fetch('http://localhost:8080/api/v1/contents', requestOptions);
    const responseData = await response.json();
    return responseData.data;
  };

  const handleClick = async (nav) => {
    if (nav.title === '글쓰기') {
      const contentId = await createPost();
      router.push(nav.link + contentId);
    } else {
      router.push(nav.link);
    }
  };

  return (
    <nav className={'flex justify-end'}>
      {navlinks.map(
        (nav) =>
          (accessToken || nav.title !== '글쓰기') && (
            <Button key={nav.title} onClick={() => handleClick(nav)}>
              {nav.title}
            </Button>
          ),
      )}
    </nav>
  );
};

export default Nav;
