'use client'

import React, { useContext, useState } from 'react'
import SearchIcon from '@mui/icons-material/Search'
import { useRouter } from 'next/navigation'
import { SearchContext } from '@/components/hooks/useSearch'

const SearchModal = ({ isVisible, onClose }) => {
  const router = useRouter()
  const [searchQuery, setSearchQuery] = useState('')
  const { setKeyword } = useContext(SearchContext)

  const handleSearch = () => {
    setKeyword(searchQuery)
    setSearchQuery('')
    router.push('/blog')
    onClose()
  }

  const handleClose = () => {
    setSearchQuery('')
    onClose()
  }

  const handleBackgroundClick = (e) => {
    if (e.target === e.currentTarget) {
      handleClose()
    }
  }

  if (!isVisible) return null

  return (
    // eslint-disable-next-line jsx-a11y/no-noninteractive-element-interactions
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-gray-800 bg-opacity-50"
      onClick={handleBackgroundClick}
      onKeyDown={(e) => {
        if (e.key === 'Escape') {
          handleClose()
        }
      }}
      tabIndex={0}
      role={'dialog'}
    >
      <div className="relative w-full max-w-md rounded-lg bg-white p-6 shadow-lg">
        <button
          className="absolute right-2 top-2 text-gray-600 hover:text-gray-900"
          onClick={handleClose}
        >
          ×
        </button>
        <h1 className="mb-4 text-2xl">검색</h1>
        <div className="flex">
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="검색어를 입력해주세요."
            className="flex-grow rounded-l-lg border border-gray-300 p-2 focus:outline-none"
          />
          <button
            type={'button'}
            onClick={handleSearch}
            className="rounded-r-lg bg-blue-500 p-2 text-white hover:bg-blue-700"
          >
            검색
          </button>
        </div>
      </div>
    </div>
  )
}

const SearchButton = () => {
  const [isModalVisible, setIsModalVisible] = useState(false)

  const handleToggleModal = () => {
    setIsModalVisible(!isModalVisible)
  }

  return (
    <div>
      <button onClick={handleToggleModal} className="text-gray-600 hover:text-gray-900">
        <SearchIcon />
      </button>
      <SearchModal isVisible={isModalVisible} onClose={handleToggleModal} />
    </div>
  )
}

export default SearchButton
