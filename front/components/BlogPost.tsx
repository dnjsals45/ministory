'use client';

import * as React from 'react';
import Card from '@mui/material/Card';
// import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
// import CardMedia from '@mui/material/CardMedia';
// import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { useRouter } from 'next/navigation';
import { Grid } from '@mui/material';

export default function MediaCard({ id, title, description }) {
  const router = useRouter();
  const getContentDetail = () => {
    console.log('id ', id);
    router.push(`/post/${id}`);
  };

  return (
    <Card sx={{ maxWidth: 345 }} onClick={getContentDetail}>
      {/*<CardMedia*/}
      {/*  sx={{ height: 140 }}*/}
      {/*  title="green iguana"*/}
      {/*/>*/}
      <CardContent>
        <Typography gutterBottom variant="h5" component="div"></Typography>
        {title}
        <Typography variant="body2" color="text.secondary">
          {description}
        </Typography>
      </CardContent>
    </Card>
  );
}
