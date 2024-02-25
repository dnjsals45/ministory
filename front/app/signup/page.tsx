import { Button } from '@/components/ui/button';
import { Separator } from '@/components/ui/separator';

export default function Component() {
  return (
    <div className="mx-auto max-w-sm space-y-6">
      <div className="space-y-2 text-center">
        <h1 className="text-3xl font-bold">Login</h1>
        <p className="text-gray-500 dark:text-gray-400">
          Enter your email below to login to your account
        </p>
      </div>
      <div>
        <Separator className="my-8" />
        <div className="space-y-4">
          <Button className="w-full" variant="outline">
            Login with Google
          </Button>
          <Button className="w-full" variant="outline">
            Login with GitHub
          </Button>
        </div>
      </div>
    </div>
  );
}
