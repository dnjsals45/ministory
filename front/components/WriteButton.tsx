'use client'

import { useRouter } from 'next/navigation'
import process from 'process'
import { useContext } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'
import { fetchWithCredentials } from '@/components/hooks/CustomFetch'

export default function WriteButton() {
  const { accessToken } = useContext(AuthContext)
  const router = useRouter()

  const newPost = async () => {
    try {
      const response = await fetchWithCredentials<{ data: { contentId: string } }>(
        process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/contents',
        'POST',
        accessToken
      )

      if (response.ok) {
        const { contentId } = await response.json().then((data) => data.data)
        router.push(`/blog/post/${contentId}`)
      } else if (response.status === 401) {
        localStorage.removeItem('access-token')
        router.push('/')
        alert('로그아웃 처리')
      } else {
        alert('새 게시물 생성에 실패했습니다.')
      }
    } catch (error) {
      console.error('새 게시물 생성 중 에러 발생:', error)
      alert('새 게시물 생성 중 에러가 발생했습니다.')
    }
  }

  return (
    <button
      key={'글쓰기'}
      onClick={newPost}
      className="hidden font-medium text-gray-900 dark:text-gray-100 sm:block"
    >
      글쓰기
    </button>
  )
}
