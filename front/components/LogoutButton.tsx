import Link from './Link'

export default function LogoutButton() {
  return (
    <Link
      key={'로그아웃'}
      href={'/logout'}
      className="hidden font-medium text-gray-900 dark:text-gray-100 sm:block"
    >
      로그아웃
    </Link>
  )
}
