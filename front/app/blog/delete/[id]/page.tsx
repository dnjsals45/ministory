'use client'

import { useRouter } from 'next/navigation'
import process from 'process'
import { useContext, useEffect } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'

export default function DeleteContent(props) {
  const id = props.params.id
  const router = useRouter()
  const { accessToken } = useContext(AuthContext)

  useEffect(() => {
    const deleteContent = async () => {
      const response = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          Authorization: 'Bearer ' + accessToken,
        },
      })

      if (response.ok) {
        alert('게시글이 삭제되었습니다')
        router.push('/blog')
      } else {
        alert('게시글 삭제 실패')
        router.back()
      }
    }

    deleteContent()
  }, [id, accessToken, router])

  return null
}
