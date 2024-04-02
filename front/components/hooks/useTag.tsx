'use client'

import { createContext, ReactNode, useEffect, useState } from 'react'
import { ContentTag } from '@/data/ContentTag'
import process from 'process'

const TagContext = createContext({
  tags: null as ContentTag[] | null,
  fetchTags: async () => {},
})

interface Props {
  children: ReactNode | ReactNode[]
}

export async function fetchTagData(): Promise<{ data: { tags: ContentTag[] } }> {
  const data = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/tags/counts', {
    method: 'GET',
  })
  return data.json()
}

const TagProvider = ({ children }: Props) => {
  const [tags, setTags] = useState<ContentTag[]>([])

  useEffect(() => {
    fetchTags()
  }, [])

  const fetchTags = async () => {
    const response = await fetchTagData()
    setTags(response.data.tags)
  }

  return <TagContext.Provider value={{ tags, fetchTags }}>{children}</TagContext.Provider>
}

export { TagProvider, TagContext }
