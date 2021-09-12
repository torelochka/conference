INSERT INTO  room (title) VALUES
                                ('первая'),
                                ('вторая'),
                                ('третья'),
                                ('четвертая')
ON CONFLICT (title) DO NOTHING;