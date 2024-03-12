'use client'

import { useRouter } from 'next/navigation'
import { useEffect } from 'react'

const GoogleCallback = () => {
  const router = useRouter()

  useEffect(() => {
    const url = new URL(window.location.href)
    const authorizationCode = url.searchParams.get('code')

    const fetchData = async () => {
      await fetch('http://localhost:8080/api/v1/users/auth/google?code=' + authorizationCode, {
        method: 'GET',
      }).then((response) => {
        const accessToken = response.headers.get('Access-Token')
        if (accessToken) localStorage.setItem('access-token', accessToken)
        router.push('/')
      })
    }

    fetchData()
  }, [])

  return null
}

export default GoogleCallback
