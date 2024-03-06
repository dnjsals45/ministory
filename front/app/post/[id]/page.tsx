'use client';

import Container from '@/components/Container';
import { useEffect, useState } from 'react';
import { ContentDetail } from '@/data/ContentDetail';
import dynamic from 'next/dynamic';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import PostTitle from '@/components/post/PostTitle';

const Viewer = dynamic(() => import('@toast-ui/react-editor').then((module) => module.Viewer), {
  ssr: false,
});

async function fetchData(id): Promise<{ data: ContentDetail }> {
  const data = await fetch(`http://localhost:8080/api/v1/contents/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
  return data.json();
}

const ContentDetail = (props) => {
  const id: number = props.params.id;
  const [detail, setDetail] = useState<ContentDetail | undefined>(undefined);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const contentData = async (id: number) => {
      const response = await fetchData(id);
      setDetail(response.data);
      setLoading(false);
    };

    contentData(id);
  }, [id]);

  return (
    <Container>
      <div className={'ml-12 mr-12 px-12 py-12'}>
        <div className={'py-2 mb-6'}>
          <PostTitle
            title={detail?.content.title}
            date={'2024-03-06'}
            views={detail?.content.views}
          />
        </div>
        <div className={'px-6 py-6 mb-6 bg-slate-50'}>
          {!loading && <Viewer initialValue={detail?.content.body} />}
        </div>
        <div className={'pt-5'}>댓글달기~</div>
      </div>
    </Container>
  );
};

export default ContentDetail;
