import SectionContainer from '@/components/SectionContainer'
import ScrollTopAndComment from '@/components/ScrollTopAndComment'
import PageTitle from '@/components/PageTitle'
import dynamic from 'next/dynamic'
import { ContentDetail } from '@/data/ContentDetail'

const Viewer = dynamic(() => import('@toast-ui/react-editor').then((module) => module.Viewer), {
  ssr: false,
})

interface LayoutProps {
  content: ContentDetail
}

export default function MyPost({ content }: LayoutProps) {
  const dateTime = content.content.createdAt.toString().split('T')[0]

  return (
    <SectionContainer>
      <ScrollTopAndComment />
      <article>
        <div>
          <header>
            <div className="space-y-1 border-b border-gray-200 pb-10 text-center dark:border-gray-700">
              <dl>
                <div>
                  <dt className="sr-only">Published</dt>
                  <dd className="text-base font-medium leading-6 text-gray-500 dark:text-gray-400">
                    <time dateTime={dateTime}>{dateTime}</time>
                  </dd>
                </div>
              </dl>
              <div className="py-3">
                <PageTitle>{content.content.title}</PageTitle>
              </div>
              <div>
                <button className="ml-4 rounded-lg bg-blue-500 px-4 py-2 text-white">수정</button>
                <button className="ml-2 rounded-lg bg-red-500 px-4 py-2 text-white">삭제</button>
              </div>
            </div>
          </header>
          <div className="grid-rows-[auto_1fr] divide-y divide-gray-200 pb-8 dark:divide-gray-700 xl:divide-y-0">
            <div className="divide-y divide-gray-200 dark:divide-gray-700 xl:col-span-3 xl:row-span-2 xl:pb-0">
              <div className="prose max-w-none pb-8 pt-10 dark:prose-invert">
                <Viewer initialValue={content.content.body} />
              </div>
            </div>
          </div>
        </div>
      </article>
    </SectionContainer>
  )
}
