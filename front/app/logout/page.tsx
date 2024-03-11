'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';

const LogOut = () => {
  const router = useRouter();

  useEffect(() => {
    localStorage.removeItem('access-token');
    router.push('/');
  }, []);

  return null;
};

export default LogOut;
