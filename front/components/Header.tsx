'use client'

import siteMetadata from '@/data/siteMetadata'
import headerNavLinks from '@/data/headerNavLinks'
import Logo from '@/data/logo.svg'
import Link from './Link'
import MobileNav from './MobileNav'
import ThemeSwitch from './ThemeSwitch'
import WriteButton from '@/components/WriteButton'
import { useContext } from 'react'
import { AuthContext } from '@/components/hooks/useAuth'
import LogoutButton from '@/components/LogoutButton'
import LoginButton from '@/components/LoginButton'
import { SearchContext } from '@/components/hooks/useSearch'
import MoreButton from '@/components/MoreButton'
import SearchButton from '@/components/SearchButton'

const Header = () => {
  const { setKeyword, setNowTag } = useContext(SearchContext)
  const { userInfo } = useContext(AuthContext)

  const handleBlogButton = () => {
    setKeyword(undefined)
    setNowTag(undefined)
  }

  return (
    <header className="flex items-center justify-between py-10">
      <div>
        <Link href="/" aria-label={siteMetadata.headerTitle}>
          <div className="flex items-center justify-between">
            <div className="mr-3">
              <Logo />
            </div>
            {typeof siteMetadata.headerTitle === 'string' ? (
              <div className="hidden h-6 text-2xl font-semibold sm:block">
                {siteMetadata.headerTitle}
              </div>
            ) : (
              siteMetadata.headerTitle
            )}
          </div>
        </Link>
      </div>
      <div className="flex items-center space-x-4 leading-5 sm:space-x-6">
        {headerNavLinks
          .filter((link) => link.href !== '/')
          .map((link) => (
            <Link
              key={link.title}
              href={link.href}
              onClick={link.title === 'Blog' ? handleBlogButton : undefined}
              className="hidden font-medium text-gray-900 dark:text-gray-100 sm:block"
            >
              {link.title}
            </Link>
          ))}
        {userInfo?.role === 'ROLE_ADMIN' && <WriteButton />}
        {userInfo === null ? <LoginButton /> : <LogoutButton />}
        <SearchButton />
        <ThemeSwitch />
        {userInfo?.role === 'ROLE_ADMIN' && <MoreButton />}
        <MobileNav />
      </div>
    </header>
  )
}

export default Header
