'use client';

import { Button } from '@/components/ui/button';
import { Separator } from '@/components/ui/separator';
import { useRouter } from 'next/navigation';
import * as process from 'process';

const SignUp = () => {
  const router = useRouter();

  const handleGoogleLogin = () => {
    router.push('http://localhost:8080/oauth2/authorization/google');
  };

  const handleGithubLogin = async () => {
    const clientId = process.env.NEXT_PUBLIC_GITHUB_CLIENT_ID;
    const redirectUri = process.env.NEXT_PUBLIC_GITHUB_REDIRECT_URI;
    window.location.href =
      'https://github.com/login/oauth/authorize?client_id=' +
      clientId +
      '&redirect_uri=' +
      redirectUri;
  };

  return (
    <div className="mx-auto max-w-sm space-y-6">
      <div className="space-y-2 text-center">
        <h1 className="text-3xl font-bold">Login</h1>
      </div>
      <div>
        <Separator className="my-8" />
        <div className="space-y-4">
          <Button className="w-full" variant="outline" onClick={handleGoogleLogin}>
            Login with Google
          </Button>
          <Button className="w-full" variant="outline" onClick={handleGithubLogin}>
            Login with GitHub
          </Button>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
