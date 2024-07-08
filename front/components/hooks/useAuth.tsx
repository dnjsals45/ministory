'use client'

import { createContext, ReactNode, useEffect, useState } from 'react'
import { UserInfo } from '@/data/UserInfo'
import process from 'process'
import { fetchWithAuthorization } from '@/components/hooks/CustomFetch'

const AuthContext = createContext({
  accessToken: '',
  userInfo: null as UserInfo | null,
  getUserInfo: async () => {},
  logout: () => {},
  setAccessToken: (token: string) => {},
})

interface Props {
  children: ReactNode | ReactNode[]
}
export async function fetchUserInfo(accessToken: string): Promise<{ data: UserInfo }> {
  const response = await fetchWithAuthorization(
    process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/users',
    'GET',
    accessToken
  )

  return response.json()
}

const AuthProvider = ({ children }: Props) => {
  const [accessToken, setAccessToken] = useState<string>('')
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null)

  useEffect(() => {
    getUserInfo()
  }, [])

  const getUserInfo = async () => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('access-token')
      if (token) {
        const response = await fetchUserInfo(token)
        setAccessToken(token)
        setUserInfo(response.data)
      } else {
        setAccessToken('')
        setUserInfo(null)
      }
    }
  }

  const logout = async () => {
    await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/users/logout', {
      method: 'GET',
      credentials: 'include',
    })

    if (typeof window !== 'undefined') {
      localStorage.removeItem('access-token')
    }
    setAccessToken('')
    setUserInfo(null)
  }

  return (
    <AuthContext.Provider value={{ accessToken, userInfo, getUserInfo, logout, setAccessToken }}>
      {children}
    </AuthContext.Provider>
  )
}

export { AuthProvider, AuthContext }
