'use client';

import Container from '@/components/Container';
import { useState, useEffect } from 'react';
import { ContentItem } from '@/data/RecentContent';
import BlogPost from '@/components/BlogPost';
import { Grid } from '@mui/material';

async function fetchData(): Promise<{ data: { contents: ContentItem[] } }> {
  const data = await fetch('http://localhost:8080/api/v1/contents', {
    method: 'GET',
  });
  return data.json();
}

const Post = () => {
  const [contents, setContents] = useState<ContentItem[]>([]);

  useEffect(() => {
    const fetchContents = async () => {
      const response = await fetchData();
      setContents(response.data.contents);
    };

    fetchContents();
  }, []);

  return (
    <Container>
      <Grid container spacing={3} justifyContent={'center'}>
        {contents.map((item, index) => (
          <Grid item xs={12} sm={6} md={4}>
            <BlogPost
              key={index}
              id={item.content.contentId}
              title={item.content.title}
              description={item.content.body}
            ></BlogPost>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default Post;
