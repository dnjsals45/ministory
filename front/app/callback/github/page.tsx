'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';

const GithubCallback = () => {
  const router = useRouter();

  useEffect(() => {
    const url = new URL(window.location.href);
    const authorizationCode = url.searchParams.get('code');

    const fetchData = async () => {
      await fetch('http://localhost:8080/api/v1/users/auth/github?code=' + authorizationCode, {
        method: 'GET',
      }).then((response) => {
        const accessToken = response.headers.get('Access-Token');
        if (accessToken) localStorage.setItem('access-token', accessToken);
        router.push('/');
      });
    };

    fetchData();
  }, []);

  return null; // 혹은 원하는 다른 JSX
};
export default GithubCallback;
