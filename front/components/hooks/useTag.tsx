'use client'

import { createContext, ReactNode, useEffect, useState } from 'react'
import { ContentTag } from '@/data/ContentTag'
import process from 'process'
import { RegisterTag } from '@/data/RegisterTag'

const TagContext = createContext({
  tags: null as RegisterTag[] | null,
  contentTags: null as ContentTag[] | null,
  fetchContentTag: async () => {},
  fetchRegisterTag: async () => {},
})

interface Props {
  children: ReactNode | ReactNode[]
}

export async function fetchRegisterTagData(): Promise<{ data: { tags: RegisterTag[] } }> {
  const data = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/tags', {
    method: 'GET',
    credentials: 'include',
  })
  return data.json()
}

export async function fetchTagData(): Promise<{ data: { tags: ContentTag[] } }> {
  const data = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/tags/counts', {
    method: 'GET',
    credentials: 'include',
  })
  return data.json()
}

const TagProvider = ({ children }: Props) => {
  const [contentTags, setContentTags] = useState<ContentTag[]>([])
  const [tags, setTags] = useState<RegisterTag[]>([])

  useEffect(() => {
    fetchContentTag()
    fetchRegisterTag()
  }, [])

  const fetchContentTag = async () => {
    const response = await fetchTagData()
    setContentTags(response.data.tags)
  }

  const fetchRegisterTag = async () => {
    const response = await fetchRegisterTagData()
    setTags(response.data.tags)
  }

  return (
    <TagContext.Provider value={{ tags, contentTags, fetchContentTag, fetchRegisterTag }}>
      {children}
    </TagContext.Provider>
  )
}

export { TagProvider, TagContext }
