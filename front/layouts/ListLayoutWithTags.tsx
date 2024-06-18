/* eslint-disable jsx-a11y/anchor-is-valid */
'use client'

import { usePathname, useSearchParams } from 'next/navigation'
import { formatDate } from 'pliny/utils/formatDate'
import Link from '@/components/Link'
import Tag from '@/components/Tag'
import siteMetadata from '@/data/siteMetadata'
import { ContentItem } from '@/data/ContentItem'
import { useContext, useEffect, useState } from 'react'
import { TagContext } from '@/components/hooks/useTag'
import process from 'process'
import { fetchWithoutAuthorization } from '@/components/hooks/CustomFetch'
import { SearchContext } from '@/components/hooks/useSearch'

interface PaginationProps {
  totalPages: number
  currentPage: number
}
interface ListLayoutProps {
  title: string
}

function Pagination({ totalPages, currentPage }: PaginationProps) {
  const pathname = usePathname()
  const basePath = pathname.split('/')[1]
  const prevPage = currentPage - 1 > 0
  const nextPage = currentPage + 1 <= totalPages

  return (
    <div className="space-y-2 pb-8 pt-6 md:space-y-5">
      <nav className="flex justify-between">
        {!prevPage && (
          <button className="cursor-auto disabled:opacity-50" disabled={!prevPage}>
            Previous
          </button>
        )}
        {prevPage && (
          <Link
            href={currentPage - 1 === 1 ? `/${basePath}/` : `/${basePath}/?page=${currentPage - 1}`}
            rel="prev"
          >
            Previous
          </Link>
        )}
        <span>
          {currentPage} of {totalPages}
        </span>
        {!nextPage && (
          <button className="cursor-auto disabled:opacity-50" disabled={!nextPage}>
            Next
          </button>
        )}
        {nextPage && (
          <Link href={`/${basePath}/?page=${currentPage + 1}`} rel="next">
            Next
          </Link>
        )}
      </nav>
    </div>
  )
}

export async function fetchContentsData(
  pageNumber: number,
  tag?: string,
  keyword?: string
): Promise<{ data: { contents: ContentItem[]; totalPage: number } }> {
  const queryParams = new URLSearchParams()

  queryParams.append('page', pageNumber.toString())

  if (keyword) {
    queryParams.append('keyword', keyword.toString())
  }

  if (tag) {
    queryParams.append('tag', tag.toString())
  }

  const response = await fetchWithoutAuthorization(
    process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents?${queryParams.toString()}`,
    'GET'
  )

  return response.json()
}

export default function ListLayoutWithTags({ title }: ListLayoutProps) {
  const params = useSearchParams().get('page')
  const pageNumber = params !== null ? parseInt(params) : 1
  const [contents, setContents] = useState<ContentItem[]>([])
  const [pagination, setPagination] = useState<PaginationProps | null>(null)
  const { contentTags } = useContext(TagContext)
  const { keyword, nowTag, setKeyword, setNowTag } = useContext(SearchContext)

  useEffect(() => {
    const fetchContents = async () => {
      const contentsData = await fetchContentsData(pageNumber, nowTag, keyword)
      setContents(contentsData.data.contents)
      setPagination({
        currentPage: pageNumber,
        totalPages: contentsData.data.totalPage,
      })
    }

    fetchContents()
  }, [pageNumber, nowTag, keyword])

  const handleTagClick = (tagName: string) => {
    setNowTag(tagName)
    setKeyword(undefined)
  }

  const handleAllPost = () => {
    setNowTag(undefined)
    setKeyword(undefined)
  }

  return (
    <>
      <div>
        <div className="pb-6 pt-6">
          <h1 className="text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:hidden sm:text-4xl sm:leading-10 md:text-6xl md:leading-14">
            {title}
          </h1>
        </div>
        <div className="flex sm:space-x-24">
          <div className="hidden h-full max-h-screen min-w-[280px] max-w-[280px] flex-wrap overflow-auto rounded bg-gray-50 pt-5 shadow-md dark:bg-gray-900/70 dark:shadow-gray-800/40 sm:flex">
            <div className="px-6 py-4">
              <h3 className="pb-2 font-bold uppercase text-primary-500">Category</h3>
              <ul>
                <button
                  onClick={handleAllPost}
                  className="text-m px-3 pt-2 font-bold uppercase text-gray-700 hover:text-primary-500 dark:text-gray-300 dark:hover:text-primary-500"
                >
                  전체 ({contentTags?.total})
                </button>
                {contentTags?.tags.map((t) => {
                  return (
                    <li key={t.tagName} className="my-3">
                      <button
                        onClick={() => handleTagClick(t.tagName)}
                        className="px-3 text-sm font-medium uppercase text-gray-500 hover:text-primary-500 dark:text-gray-300 dark:hover:text-primary-500"
                      >
                        {`${t.tagName} (${t.count})`}
                      </button>
                    </li>
                  )
                })}
              </ul>
            </div>
          </div>
          <div>
            <ul>
              {contents.map((post) => {
                const { content, user } = post
                return (
                  <li key={content.contentId} className="py-5">
                    <article className="flex flex-col space-y-2 xl:space-y-0">
                      <dl>
                        <dt className="sr-only">Published on</dt>
                        <dd className="text-base font-medium leading-6 text-gray-500 dark:text-gray-400">
                          <time dateTime={content.createdAt.toString()}>
                            {formatDate(content.createdAt.toString(), siteMetadata.locale)}
                          </time>
                        </dd>
                      </dl>
                      <div className="space-y-3">
                        <div>
                          <h2 className="text-2xl font-bold leading-8 tracking-tight">
                            <Link
                              href={`/blog/${content.contentId}`}
                              className="text-gray-900 hover:text-gray-500 dark:text-gray-100"
                            >
                              {content.title}
                            </Link>
                          </h2>
                          <div className="flex flex-wrap">
                            {content.tags?.map((tag) => (
                              <button
                                key={tag.tagName}
                                className="mr-3 text-sm font-medium uppercase text-primary-500 hover:text-primary-600 dark:hover:text-primary-400"
                                onClick={() => handleTagClick(tag.tagName)}
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
                    </article>
                  </li>
                )
              })}
            </ul>
            {pagination && (
              <Pagination currentPage={pagination.currentPage} totalPages={pagination.totalPages} />
            )}
          </div>
        </div>
      </div>
    </>
  )
}
