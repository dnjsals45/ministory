'use client'

import { useContext, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { AuthContext } from '@/components/hooks/useAuth'
import process from 'process'

const GithubCallback = () => {
  const router = useRouter()
  const { getUserInfo } = useContext(AuthContext)

  useEffect(() => {
    const url = new URL(window.location.href)
    const authorizationCode = url.searchParams.get('code')

    const fetchData = async () => {
      await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/users/auth/github?code=' + authorizationCode,
        {
          method: 'GET',
          credentials: 'include',
        }
      ).then((response) => {
        const accessToken = response.headers.get('Access-Token')
        if (accessToken) localStorage.setItem('access-token', accessToken)
        getUserInfo()
        router.push('/')
      })
    }

    fetchData()
  }, [])

  return null
}
export default GithubCallback
