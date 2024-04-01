import ListLayout from '@/layouts/ListLayoutWithTags'
import { genPageMetadata } from 'app/seo'
import { Suspense } from 'react'

export const metadata = genPageMetadata({ title: 'Blog' })

export default function BlogPage() {
  return (
    <div>
      <Suspense fallback={<div>Loading...</div>}>
        <ListLayout title="All Posts" />
      </Suspense>
    </div>
  )
}
