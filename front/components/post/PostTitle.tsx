import Typography from '@mui/material/Typography';
import { Grid } from '@mui/material';

const PostTitle = ({ title, date, views }) => {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Typography variant="h3" gutterBottom>
          {title}
        </Typography>
      </Grid>
      <Grid item xs={12} textAlign="right" style={{ color: '#6c7477' }}>
        <Typography variant="subtitle2">
          작성일: {date} | 조회수: {views}
        </Typography>
      </Grid>
    </Grid>
  );
};

export default PostTitle;
