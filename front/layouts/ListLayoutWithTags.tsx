/* eslint-disable jsx-a11y/anchor-is-valid */
'use client'

import { usePathname, useSearchParams } from 'next/navigation'
import { slug } from 'github-slugger'
import { formatDate } from 'pliny/utils/formatDate'
import Link from '@/components/Link'
import Tag from '@/components/Tag'
import siteMetadata from '@/data/siteMetadata'
import tagData from 'app/tag-data.json'
import { ContentItem } from '@/data/ContentItem'
import { useEffect, useState } from 'react'

interface PaginationProps {
  totalPages: number
  currentPage: number
}
interface ListLayoutProps {
  title: string
  postPerPage: number
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

async function fetchData(
  pageNumber: number
): Promise<{ data: { contents: ContentItem[]; totalPage: number } }> {
  const data = await fetch(`http://localhost:8080/api/v1/contents?page=${pageNumber}`, {
    method: 'GET',
  })
  return data.json()
}

export default function ListLayoutWithTags({ title, postPerPage }: ListLayoutProps) {
  const params = useSearchParams().get('page')
  const pageNumber = params !== null ? parseInt(params) : 1
  const [contents, setContents] = useState<ContentItem[]>([])
  const [totalPage, setTotalPage] = useState<number>(1)
  const [pagination, setPagination] = useState<PaginationProps | null>(null)
  const pathname = usePathname()
  const tagCounts = tagData as Record<string, number>
  const tagKeys = Object.keys(tagCounts)
  const sortedTags = tagKeys.sort((a, b) => tagCounts[b] - tagCounts[a])

  useEffect(() => {
    const fetchContents = async () => {
      const response = await fetchData(pageNumber)
      setContents(response.data.contents)
      setTotalPage(response.data.totalPage)
      setPagination({
        currentPage: pageNumber,
        totalPages: response.data.totalPage,
      })
    }

    fetchContents()
  }, [pageNumber])

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
              {pathname.startsWith('/blog') ? (
                <h3 className="font-bold uppercase text-primary-500">Category</h3>
              ) : (
                <Link
                  href={`/blog`}
                  className="font-bold uppercase text-gray-700 hover:text-primary-500 dark:text-gray-300 dark:hover:text-primary-500"
                >
                  All Posts
                </Link>
              )}
              <ul>
                {sortedTags.map((t) => {
                  return (
                    <li key={t} className="my-3">
                      {pathname.split('/tags/')[1] === slug(t) ? (
                        <h3 className="inline px-3 py-2 text-sm font-bold uppercase text-primary-500">
                          {`${t} (${tagCounts[t]})`}
                        </h3>
                      ) : (
                        <Link
                          href={`/tags/${slug(t)}`}
                          className="px-3 py-2 text-sm font-medium uppercase text-gray-500 hover:text-primary-500 dark:text-gray-300 dark:hover:text-primary-500"
                          aria-label={`View posts tagged ${t}`}
                        >
                          {`${t} (${tagCounts[t]})`}
                        </Link>
                      )}
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
                              className="text-gray-900 dark:text-gray-100"
                            >
                              {content.title}
                            </Link>
                          </h2>
                          {/*<div className="flex flex-wrap">*/}
                          {/*  {tags?.map((tag) => <Tag key={tag} text={tag} />)}*/}
                          {/*</div>*/}
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
