import { useRouter } from 'next/navigation'

export default function TempContentButton() {
  const router = useRouter()

  const handleClick = () => {
    router.push('/manage/temp_contents')
  }

  return (
    <button
      className="block w-full px-4 py-2 text-left text-gray-800 hover:bg-gray-200 dark:text-gray-200 dark:hover:bg-gray-700"
      style={{ whiteSpace: 'nowrap' }}
      onClick={handleClick}
    >
      임시 저장
    </button>
  )
}
