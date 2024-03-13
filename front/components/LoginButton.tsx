import Link from './Link'

export default function LoginButton() {
  return (
    <Link
      key={'로그인'}
      href={'/signup'}
      className="hidden font-medium text-gray-900 dark:text-gray-100 sm:block"
    >
      로그인
    </Link>
  )
}
