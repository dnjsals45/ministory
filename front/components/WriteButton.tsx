import Link from './Link'

export default function WriteButton() {
  return (
    <Link
      key={'글쓰기'}
      href={'/blog/post'}
      className="hidden font-medium text-gray-900 dark:text-gray-100 sm:block"
    >
      글쓰기
    </Link>
  )
}
