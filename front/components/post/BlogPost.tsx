'use client';

import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { useRouter } from 'next/navigation';
import RecentPostTitle from '@/components/post/RecentPostTitle';
import { Grid } from '@mui/material';
import VisibilityOutlinedIcon from '@mui/icons-material/VisibilityOutlined';
import ModeEditOutlineOutlinedIcon from '@mui/icons-material/ModeEditOutlineOutlined';

export default function MediaCard({ data }) {
  const router = useRouter();
  const moveContentDetail = () => {
    router.push(`/post/${data.content.contentId}`);
  };

  const handleIconClick = (event) => {
    event.stopPropagation(); // 이벤트 전파 중지
  };

  return (
    <Card sx={{ maxWidth: 350, height: 350 }} onClick={moveContentDetail}>
      <CardMedia sx={{ height: 130 }} component="img" src="/logo.jpg" />
      <CardContent>
        <RecentPostTitle title={data.content.title} />
        <Typography
          variant="body2"
          color="text.secondary"
          style={{ height: '150px', wordBreak: 'break-all' }}
        >
          {data.content.body}
        </Typography>
        <Grid container justifyContent="flex-end" onClick={handleIconClick}>
          <Typography variant="caption" style={{ color: '#Aab5b9' }}>
            <ModeEditOutlineOutlinedIcon fontSize="small" /> {data.content.createdAt.split('T')[0]}{' '}
            | <VisibilityOutlinedIcon fontSize="small" /> {data.content.views}
          </Typography>
        </Grid>
      </CardContent>
    </Card>
  );
}
