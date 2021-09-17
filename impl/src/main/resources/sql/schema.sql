INSERT INTO  room (title) VALUES
                                ('First'),
                                ('Second'),
                                ('Third'),
                                ('Fourth')
ON CONFLICT (title) DO NOTHING;
