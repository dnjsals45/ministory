import Link from './Link'

export default function MobileLogoutButton() {
  return (
    <Link
      key={'로그아웃'}
      href={'/logout'}
      className="text-2xl font-bold tracking-widest text-gray-900 dark:text-gray-100"
    >
      로그아웃
    </Link>
  )
}
