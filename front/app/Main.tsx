'use client'

import { useEffect, useState } from 'react'
import { ContentItem } from '@/data/ContentItem'
import projectsData from '@/data/projectsData'
import RecentPostCard from '@/components/RecentPostCard'

async function fetchData(): Promise<{ data: { contents: ContentItem[] } }> {
  const data = await fetch('http://localhost:8080/api/v1/contents/recent', {
    method: 'GET',
  })
  return data.json()
}

export default function Home() {
  const [contents, setContents] = useState<ContentItem[]>([])

  useEffect(() => {
    const fetchContents = async () => {
      const response = await fetchData()
      setContents(response.data.contents)
    }

    fetchContents()
  }, [])

  return (
    <>
      <div className="divide-y divide-gray-200 dark:divide-gray-700">
        <div className="divide-y divide-gray-200 dark:divide-gray-700">
          <div className="space-y-2 pb-8 pt-6 md:space-y-5">
            <h1 className="text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14">
              최근 게시물
            </h1>
          </div>
          <div className="container py-12">
            <div className="-m-4 flex flex-wrap">
              {contents.map((content, index) => (
                <RecentPostCard key={index} data={content} />
              ))}
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
