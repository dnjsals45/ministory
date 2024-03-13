'use client'

import { useRouter } from 'next/navigation'
import { useContext } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'

export default function LogOut() {
  const router = useRouter()
  const { logout } = useContext(AuthContext)
  if (typeof window !== 'undefined') {
    localStorage.removeItem('access-token')
    logout()
    router.push('/')
  }
}
