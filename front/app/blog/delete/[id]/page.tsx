'use client'

import { useRouter } from 'next/navigation'
import process from 'process'
import { useContext, useEffect } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'
import { fetchWithAuthorization } from '@/components/hooks/CustomFetch'

export default function DeleteContent(props) {
  const id = props.params.id
  const router = useRouter()
  const { accessToken, setAccessToken } = useContext(AuthContext)

  useEffect(() => {
    const deleteContent = async () => {
      const response = await fetchWithAuthorization(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`,
        'DELETE',
        accessToken
      )

      if (response.headers.has('Access-Token')) {
        const newAccessToken = response.headers.get('Access-Token')
        newAccessToken && setAccessToken(newAccessToken)
      }

      if (response.ok) {
        alert('게시글이 삭제되었습니다')
        router.push('/blog')
      } else {
        alert('게시글 삭제 실패')
        router.back()
      }
    }

    deleteContent()
  }, [])

  return null
}
