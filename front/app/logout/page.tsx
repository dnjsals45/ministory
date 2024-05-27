'use client'

import { useRouter } from 'next/navigation'
import { useContext, useEffect } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'

export default function LogOut() {
  const router = useRouter()
  const { logout } = useContext(AuthContext)

  useEffect(() => {
    const fetchLogout = async () => {
      await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/users/logout', {
        method: 'GET',
        credentials: 'include',
      }).then(() => {
        logout()
      })
    }

    fetchLogout()
    router.push('/')
  }, [])
  return null
}
