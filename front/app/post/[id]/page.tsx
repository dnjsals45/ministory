'use client';

import Container from '@/components/Container';
import { useEffect, useState } from 'react';
import { ContentDetail } from '@/data/ContentDetail';
import dynamic from 'next/dynamic';

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
      <div className={'ml-12 mr-12 pl-12 pr-12 py-12'}>
        <h1 className={'font-bold text-6xl'}>{detail?.content.title}</h1>
      </div>
      <div className={'ml-12 mr-12 pl-12 pr-12 py-12'}>
        {!loading && <Viewer initialValue={detail?.content.body} />}
      </div>
    </Container>
  );
};

export default ContentDetail;
