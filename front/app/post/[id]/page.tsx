'use client';

import Container from '@/components/Container';
import { useEffect, useState } from 'react';
import { ContentDetail } from '@/data/ContentDetail';

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

  useEffect(() => {
    const contentData = async (id: number) => {
      const response = await fetchData(id);
      setDetail(response.data);
    };

    contentData(id);
  }, [id]);

  return (
    <Container>
      <h1>상세 글 조회</h1>
      <div>{detail?.content.contentId}</div>
    </Container>
  );
};

export default ContentDetail;
