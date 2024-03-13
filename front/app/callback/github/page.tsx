'use client'

import { useContext, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { AuthContext } from '@/components/hooks/useAuth'

const GithubCallback = () => {
  const router = useRouter()
  const { getUserInfo } = useContext(AuthContext)

  useEffect(() => {
    const url = new URL(window.location.href)
    const authorizationCode = url.searchParams.get('code')

    const fetchData = async () => {
      await fetch('http://localhost:8080/api/v1/users/auth/github?code=' + authorizationCode, {
        method: 'GET',
      }).then((response) => {
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
