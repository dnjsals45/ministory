'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import process from 'process'

interface newPostData {
  contentId: string
}

export async function createPost(requestOptions: {
  method: string
  headers: { 'Content-Type': string }
}): Promise<{ data: newPostData }> {
  const response = await fetch(
    process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/contents',
    requestOptions
  )

  return response.json()
}

export default function MobileWriteButton() {
  const router = useRouter()
  const [contentId, setContentId] = useState<string>('')
  const newPost = async () => {
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    }

    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('access-token')
      if (token) {
        requestOptions.headers['Authorization'] = 'Bearer ' + token
      }
    }

    const response = await createPost(requestOptions)
    setContentId(response.data.contentId)
    router.push(`/blog/post/${contentId}`)
  }

  return (
    <button
      key={'글쓰기'}
      onClick={newPost}
      className="text-2xl font-bold tracking-widest text-gray-900 dark:text-gray-100"
    >
      글쓰기
    </button>
  )
}
