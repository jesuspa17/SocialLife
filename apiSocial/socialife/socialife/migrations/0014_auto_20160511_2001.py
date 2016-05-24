# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0013_categoria_plan'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='plan',
            name='categoria',
        ),
        migrations.RemoveField(
            model_name='plan',
            name='usuario',
        ),
        migrations.DeleteModel(
            name='Categoria',
        ),
        migrations.DeleteModel(
            name='Plan',
        ),
    ]
