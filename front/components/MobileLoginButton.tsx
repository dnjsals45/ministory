import Link from './Link'

export default function MobileLoginButton() {
  return (
    <Link
      key={'로그인'}
      href={'/signup'}
      className="text-2xl font-bold tracking-widest text-gray-900 dark:text-gray-100"
    >
      로그인
    </Link>
  )
}
