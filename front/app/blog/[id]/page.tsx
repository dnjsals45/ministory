'use client'

import PostSimple from '@/layouts/PostSimple'
import PostLayout from '@/layouts/PostLayout'
import PostBanner from '@/layouts/PostBanner'
import MyPost from '@/layouts/MyPost'
import '@toast-ui/editor/dist/toastui-editor-viewer.css'
import { ContentDetail } from '@/data/ContentDetail'
import { useEffect, useState } from 'react'

const layouts = {
  PostSimple,
  PostLayout,
  PostBanner,
}

const Layout = MyPost

async function fetchData(id: number): Promise<{ data: ContentDetail }> {
  const data = await fetch(`http://localhost:8080/api/v1/contents/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  })
  return data.json()
}

export default function Content(props) {
  const id = props.params.id
  const [detail, setDetail] = useState<ContentDetail | undefined>(undefined)
  // const [loading, setLoading] = useState(true)
  // const [refreshComments, setRefreshComments] = useState(false) // 댓글 목록을 새로고침하기 위한 상태 추가

  useEffect(() => {
    const contentData = async (id: number) => {
      const response = await fetchData(id)
      setDetail(response.data)
      // setLoading(false);
    }

    contentData(id)
  }, [id])

  return <>{detail && <Layout content={detail} />}</>
}
