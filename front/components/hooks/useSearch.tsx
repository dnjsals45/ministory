'use client'

import { createContext, ReactNode, useState } from 'react'

interface SearchContextType {
  keyword: string | undefined
  setKeyword: React.Dispatch<React.SetStateAction<string | undefined>>
}

const SearchContext = createContext<SearchContextType>({
  keyword: undefined,
  setKeyword: () => {},
})

interface Props {
  children: ReactNode | ReactNode[]
}

const SearchProvider = ({ children }: Props) => {
  const [keyword, setKeyword] = useState<string>()

  return <SearchContext.Provider value={{ keyword, setKeyword }}>{children}</SearchContext.Provider>
}

export { SearchProvider, SearchContext }
