'use client'

import { createContext, ReactNode, useState } from 'react'

interface SearchContextType {
  keyword: string | undefined
  nowTag: string | undefined
  setKeyword: React.Dispatch<React.SetStateAction<string | undefined>>
  setNowTag: React.Dispatch<React.SetStateAction<string | undefined>>
}

const SearchContext = createContext<SearchContextType>({
  keyword: undefined,
  nowTag: undefined,
  setKeyword: () => {},
  setNowTag: () => {},
})

interface Props {
  children: ReactNode | ReactNode[]
}

const SearchProvider = ({ children }: Props) => {
  const [keyword, setKeyword] = useState<string>()
  const [nowTag, setNowTag] = useState<string>()

  return (
    <SearchContext.Provider value={{ keyword, nowTag, setKeyword, setNowTag }}>
      {children}
    </SearchContext.Provider>
  )
}

export { SearchProvider, SearchContext }
