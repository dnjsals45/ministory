'use client';

import navlinks from '../data/navlinks';
import { useRouter } from 'next/navigation';
import { Button } from '@mui/material';

const Nav = () => {
  const router = useRouter();
  const createPost = async () => {
    const accessToken = localStorage.getItem('access-token');

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
    return responseData.data; // 22
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
    <nav>
      {navlinks.map((nav) => (
        <Button key={nav.title}>
          <a className={`mr-4`} onClick={() => handleClick(nav)}>
            {nav.title}
          </a>
        </Button>
      ))}
    </nav>
  );
};

export default Nav;
