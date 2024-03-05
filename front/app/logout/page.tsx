'use client';

import { useRouter } from 'next/navigation';

const LogOut = () => {
  const router = useRouter();

  localStorage.removeItem('access-token');
  router.push('/');
};

export default LogOut;
