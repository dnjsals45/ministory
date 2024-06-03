'use client'

import { useRouter } from 'next/navigation'
import { useContext, useEffect } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'

export default function LogOut() {
  const router = useRouter()
  const { logout } = useContext(AuthContext)

  useEffect(() => {
    logout()
    router.push('/')
  }, [])
  return null
}
