'use client'

import { useRouter } from 'next/navigation'
import process from 'process'

interface newPostData {
  contentId: string
}

export async function createPost(
  requestOptions: {
    method: string
    headers: { 'Content-Type': string }
  },
  router
): Promise<{ data: newPostData }> {
  const response = await fetch(
    process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/contents',
    requestOptions
  )

  if (response.status === 401) {
    localStorage.removeItem('access-token')
    router.push('/')
    alert('로그아웃 처리')
  }

  return response.json()
}

export default function WriteButton() {
  const router = useRouter()
  const newPost = async () => {
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        credentials: 'include',
      },
    }

    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('access-token')
      if (token) {
        requestOptions.headers['Authorization'] = 'Bearer ' + token
      }
    }

    const response = await createPost(requestOptions, router)
    router.push(`/blog/post/${response.data.contentId}`)
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
