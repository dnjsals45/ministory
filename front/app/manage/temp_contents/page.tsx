'use client'

import { useContext, useEffect, useState } from 'react'
import { fetchWithAuthorization } from '@/components/hooks/CustomFetch'
import process from 'process'
import { AuthContext } from '@/components/hooks/useAuth'
import { TempContentItem } from '@/data/TempContentItem'
import siteMetadata from '@/data/siteMetadata'
import { formatDate } from 'pliny/utils/formatDate'
import Link from '@/components/Link'

export default function TempContents() {
  const { accessToken, setAccessToken } = useContext(AuthContext)
  const [tempContents, setTempContents] = useState<TempContentItem[]>([])
  const fetchTempContents = async () => {
    const response = await fetchWithAuthorization(
      process.env.NEXT_PUBLIC_BACKEND_URL + '/api/v1/contents/temp',
      'GET',
      accessToken
    )

    return response.json()
  }

  useEffect(() => {
    const getTempContents = async () => {
      const response = await fetchTempContents()
      setTempContents(response.data.contents)
    }

    if (accessToken) {
      getTempContents()
    }
  }, [accessToken]) // 새로고침 시 accessToken 이 로드되는 시간보다 fetch 받는 시간이 빨라져 문제가 생김

  return (
    <>
      <div className="divide-y divide-gray-200 dark:divide-gray-700">
        <div className="space-y-2 pb-8 pt-6 md:space-y-5">
          <h1 className="text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14">
            임시저장된 게시글
          </h1>
          {/*<p className="text-lg leading-7 text-gray-500 dark:text-gray-400"></p>*/}
        </div>
        <ul className="divide-y divide-gray-200 dark:divide-gray-700">
          {tempContents.length === 0 && 'No posts found.'}
          {tempContents.map((post) => {
            const { content, user } = post
            return (
              <li key={content.contentId} className="py-12">
                <article>
                  <div className="space-y-2 xl:grid xl:grid-cols-4 xl:items-baseline xl:space-y-0">
                    <dl>
                      <dt className="sr-only">Published on</dt>
                      <dd className="text-base font-medium leading-6 text-gray-500 dark:text-gray-400">
                        <time dateTime={content.createdAt.toString()}>
                          {formatDate(content.createdAt.toString(), siteMetadata.locale)}
                        </time>
                      </dd>
                    </dl>
                    <div className="space-y-5 xl:col-span-3">
                      <div className="space-y-6">
                        <div>
                          <h2 className="text-2xl font-bold leading-8 tracking-tight">
                            <Link
                              href={`/blog/edit/${content.uuid}`}
                              className="text-gray-900 dark:text-gray-100"
                            >
                              {content.title}
                            </Link>
                          </h2>
                          <div className="flex flex-wrap">
                            {content.tags.map((tag) => (
                              <button
                                key={tag.tagName}
                                className="mr-3 text-sm font-medium uppercase text-primary-500 hover:text-primary-600 dark:hover:text-primary-400"
                              >
                                #{tag.tagName.split(' ').join('-')}
                              </button>
                            ))}
                          </div>
                        </div>
                        <div className="prose max-w-none text-gray-500 dark:text-gray-400">
                          {content.body}
                        </div>
                      </div>
                    </div>
                  </div>
                </article>
              </li>
            )
          })}
        </ul>
      </div>
    </>
  )
}
